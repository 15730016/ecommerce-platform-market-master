import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TokenComponent} from './register/token.component';
import {HomeComponent} from './home/home.component';
import {TwoFaAuthComponent} from './two-fa-auth/two-fa-auth.component';
import {ProfileComponent} from './profile/profile.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProductListComponent} from './product/product-list/product-list.component';
import {ProductDetailComponent} from './product/product-detail/product-detail.component';
import {ProductCreateEditComponent} from './product/product-create-edit/product-create-edit.component';
import {CategoryListComponent} from './category/category-list/category-list.component';
import {CategoryDetailComponent} from './category/category-detail/category-detail.component';
import {CategoryCreateEditComponent} from './category/category-create-edit/category-create-edit.component';
import { CategoryListDeletedComponent } from './category/category-list-deleted/category-list-deleted.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'twoFaAuth', component: TwoFaAuthComponent },
  // { path: 'order', component: OrderComponent },
  { path: 'verify', component: TokenComponent },
  { path: 'product', redirectTo: 'product/list', pathMatch: 'full'},
  { path: 'product/list', component: ProductListComponent },
  { path: 'product/:id/view', component: ProductDetailComponent },
  { path: 'product/add', component: ProductCreateEditComponent },
  { path: 'product/:id/edit', component: ProductCreateEditComponent },
  { path: 'category', redirectTo: 'category/list', pathMatch: 'full'},
  { path: 'category/list', component: CategoryListComponent },
  { path: 'category/:id/view', component: CategoryDetailComponent },
  { path: 'category/add', component: CategoryCreateEditComponent },
  { path: 'category/:id/edit', component: CategoryCreateEditComponent },
  {path: 'category/deleted', component: CategoryListDeletedComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
