import { Component, OnDestroy, OnInit } from '@angular/core';
import { CategoryService } from './category.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Category } from './model/category';
import { Subscription, filter } from 'rxjs';
import { CategoryProducts } from './model/categoryProducts';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit, OnDestroy{

  categoryProducts!: CategoryProducts;
  private sub!: Subscription;
  constructor(
    private categoryService : CategoryService,
    private route: ActivatedRoute,
    private router: Router
    ){

  }
  ngOnDestroy(): void {
    // odsubskrybowac z routera  aby po wyjsciu z kategori nie pobieralo ponownie produktow z kateogriami
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.sub = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd) // filtruje eventy typu NavigationEnd
    ).subscribe(() => this.getCategoryWithProducts(0,10)) // odswiezenie danych 
    this.getCategoryWithProducts(0, 10);
  }

  getCategoryWithProducts(page: number, size: number){
    let slug = this.route.snapshot.params['slug'];
    this.categoryService.getCategoryWithProducts(slug, page, size)
    .subscribe(category => this.categoryProducts = category);
  }

  onPageEvent(event: PageEvent){
    this.getCategoryWithProducts(event.pageIndex, event.pageSize);
  }

}
