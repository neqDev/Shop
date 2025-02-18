import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DefaultComponent } from './layouts/default/default.component';
import { HomeComponent } from './modules/home/home.component';
import { ProductComponent } from './modules/product/product.component';
import { FullpageComponent } from './layouts/fullpage/fullpage.component';
import { LoginComponent } from './modules/login/login.component';
import { FullpageadminComponent } from './layouts/fullpageadmin/fullpageadmin.component';
import { AdminComponent } from './modules/admin/admin.component';
import { AdminProductComponent } from './modules/admin/admin-product/admin-product.component';
import { AdminProductAddComponent } from './modules/admin/admin-product/admin-product-add/admin-product-add.component';
import { ProductDetailsComponent } from './modules/product-details/product-details.component';
import { AdminCategoryComponent } from './modules/admin/admin-category/admin-category.component';
import { AdminCategoryAddComponent } from './modules/admin/admin-category/admin-category-add/admin-category-add.component';
import { AdminCategoryUpdateComponent } from './modules/admin/admin-category/admin-category-update/admin-category-update.component';
import { CategoryComponent } from './modules/category/category.component';
import { AdminProductUpdateComponent } from './modules/admin/admin-product/admin-product-update/admin-product-update.component';
import { AdminReviewComponent } from './modules/admin/admin-review/admin-review.component';
import { CartComponent } from './modules/cart/cart.component';
import { OrderComponent } from './modules/order/order.component';
import { AdminOrderComponent } from './modules/admin/admin-order/admin-order.component';
import { AdminOrderUpdateComponent } from './modules/admin/admin-order/admin-order-update/admin-order-update.component';
import { AdminOrderExportComponent } from './modules/admin/admin-order/admin-order-export/admin-order-export.component';
import { AdminOrderStatsComponent } from './modules/admin/admin-order/admin-order-stats/admin-order-stats.component';
import { AdminLoginComponent } from './modules/admin/admin-login/admin-login.component';
import { FullpageadminemptyComponent } from './layouts/fullpageadminempty/fullpageadminempty.component';
import { AdminAuthorizeGuard } from './modules/admin/admin-review/guard/adminAuthorizeGuard';
import { ProfileComponent } from './modules/profile/profile.component';
import { ProfileAuthorizeGuard } from './modules/common/guard/profileAuthorizeGuard';
import { LostPasswordComponent } from './modules/login/lost-password/lost-password.component';

const routes: Routes = [ // podstrony jakie mozemy ogladac w naszej aplikacji
  {
    path:'', component: DefaultComponent, children: [
      {path: '', component: HomeComponent}, // sciazka + jakie komponent ma sie zaladowac
      {path: 'products', component: ProductComponent},
      {path: 'products/:slug', component: ProductDetailsComponent},
      {path: 'categories/:slug', component: CategoryComponent},
      {path: 'cart', component: CartComponent},
      {path: 'order', component: OrderComponent},
      {path: 'profile', component: ProfileComponent, canActivate: [ProfileAuthorizeGuard]}
    ]
  },
  {
    path:'', component: FullpageComponent, children: [
      {path: 'login', component: LoginComponent},
      {path: 'lostPassword', component: LostPasswordComponent },
      {path: 'lostPassword/:hash', component: LostPasswordComponent }
    ]
  },
  {
    path:'', component: FullpageadminComponent, children: [
      {path: 'admin', component: AdminComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/products', component: AdminProductComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/products/update/:id', component: AdminProductUpdateComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/products/add', component: AdminProductAddComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/categories', component: AdminCategoryComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/categories/add', component: AdminCategoryAddComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/categories/update/:id', component: AdminCategoryUpdateComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/reviews', component: AdminReviewComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/orders', component: AdminOrderComponent, canActivate: [AdminAuthorizeGuard]},
      {path: 'admin/orders/update/:id', component: AdminOrderUpdateComponent, canActivate: [AdminAuthorizeGuard] },
      {path: 'admin/orders/export', component: AdminOrderExportComponent, canActivate: [AdminAuthorizeGuard] },
      {path: 'admin/orders/stats', component: AdminOrderStatsComponent, canActivate: [AdminAuthorizeGuard]}
    ]
  },
  {
    path:'', component: FullpageadminemptyComponent, children: [
     
      {path: 'admin/login', component: AdminLoginComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})], // onSameUrlNavigation: 'reload' przeladowuje nasz komponent kiedy zmieni sie url
  exports: [RouterModule]
})
export class AppRoutingModule { }
