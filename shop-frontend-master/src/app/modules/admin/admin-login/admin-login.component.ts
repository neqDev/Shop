import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminLoginService } from './admin-login.service';
import { JwtService } from '../../common/service/jwt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.scss']
})
export class AdminLoginComponent implements OnInit{


  formGroup!: FormGroup;
  loginError = false;

  constructor(private formBuilder: FormBuilder,
    private adminLoginService: AdminLoginService,
    private jwtService: JwtService,
    private router: Router // pozwoli na przekierowanie  
    ){}

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  submit(){
    if(this.formGroup.valid){
      this.adminLoginService.login(this.formGroup.value)
      .subscribe({
        // zwrócny poprawny rezultat
        next: (response) => {
          this.loginError = false;
          if(response.adminAccess){
            this.jwtService.setToken(response.token);
            this.jwtService.setAdminAccess(true);
          }
          this.router.navigate(["/admin"])
        },
        // zwrócny error 
        error: () => this.loginError = true
      } )
    }
  }

}
