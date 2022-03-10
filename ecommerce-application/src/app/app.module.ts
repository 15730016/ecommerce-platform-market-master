import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from "./common/header/header.component";
import {FooterComponent} from "./common/footer/footer.component";
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {TokenComponent} from './register/token.component';
import {TwoFaAuthComponent} from './two-fa-auth/two-fa-auth.component';
import {ProfileComponent} from './profile/profile.component';
import {ProductListComponent} from './product/product-list/product-list.component';
import {ProductCreateEditComponent} from './product/product-create-edit/product-create-edit.component';
import {ProductDetailComponent} from './product/product-detail/product-detail.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {ToastrModule} from "ngx-toastr";
import {CURRENCY_MASK_CONFIG, CurrencyMaskConfig, CurrencyMaskModule} from "ng2-currency-mask";
import {SlugPipe} from './pipe/slug.pipe';
import { CategoryDetailComponent } from './category/category-detail/category-detail.component';
import { CategoryCreateEditComponent } from './category/category-create-edit/category-create-edit.component';
import { CategoryListComponent } from './category/category-list/category-list.component';
import { ColorListComponent } from './color/color-list/color-list.component';
import { ColorDetailComponent } from './color/color-detail/color-detail.component';
import { ColorCreateEditComponent } from './color/color-create-edit/color-create-edit.component';
import { SizeCreateEditComponent } from './size/size-create-edit/size-create-edit.component';
import { SizeListComponent } from './size/size-list/size-list.component';
import { SizeDetailComponent } from './size/size-detail/size-detail.component';
import { CategoryListDeletedComponent } from './category/category-list-deleted/category-list-deleted.component';

export const CustomCurrencyMaskConfig: CurrencyMaskConfig = {
  align: "right",
  allowNegative: false,
  decimal: ",",
  precision: 2,
  prefix: "",
  suffix: "VNĐ",
  thousands: "."
};

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    TokenComponent,
    TwoFaAuthComponent,
    ProfileComponent,
    ProductListComponent,
    ProductCreateEditComponent,
    ProductDetailComponent,
    SlugPipe,
    CategoryDetailComponent,
    CategoryCreateEditComponent,
    CategoryListComponent,
    ColorListComponent,
    ColorDetailComponent,
    ColorCreateEditComponent,
    SizeCreateEditComponent,
    SizeListComponent,
    SizeDetailComponent,
    CategoryListDeletedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    CurrencyMaskModule,
    ToastrModule.forRoot(),
  ],
  providers: [
    { provide: CURRENCY_MASK_CONFIG, useValue: CustomCurrencyMaskConfig }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
