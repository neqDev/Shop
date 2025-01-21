import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HeaderService } from './header.service';
import { CartIconService } from 'src/app/modules/common/service/cart-icon.service';
import { JwtService } from 'src/app/modules/common/service/jwt.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  title = "Shop";
  cartProductCounter = "";
  isLoggedIn = false;

  constructor(private cookiesService: CookieService,
              private headerService: HeaderService,
              private cartIconService: CartIconService,
              private jwtService: JwtService) {}
  
  ngOnInit(): void {
      this.getCountProducts();
      // odbieramy eventy i subskrybujemy sie na ten subject w IconService
      this.cartIconService.subject
      .subscribe(counter => this.cartProductCounter = String(+counter > 0 ? counter: ""));
      this.isLoggedIn = this.jwtService.isLoggedIn();
  }

  getCountProducts(){
    this.headerService.getCountProducts(Number(this.cookiesService.get("cartId")))
    .subscribe(counter => this.cartProductCounter = String(+counter > 0 ? counter: ""));
  }
}
