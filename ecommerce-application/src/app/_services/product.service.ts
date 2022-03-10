import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs';
import {AppConstants} from '../common/app.constants';
import {Product} from "../product/product";
import {Category} from "../category/category";
import {Color} from "../color/color";
import {Size} from "../size/size";

@Injectable({providedIn: 'root'})
export class ProductService {

	constructor(private httpClient: HttpClient) {
	}

  //-------------------- Product --------------------//

	getAllProduct(request: any): Observable<any> {
		const params = request;
		return this.httpClient.get(AppConstants.MANAGEMENT_API + 'product', {params});
	}

	createProduct(product:any): Observable<any> {
		return this.httpClient.post(AppConstants.MANAGEMENT_API + 'product', JSON.stringify(product), AppConstants.httpOptions)
	}

	findProduct(id:number): Observable<any> {
		return this.httpClient.get<Product>(AppConstants.MANAGEMENT_API + 'product/' + id)
	}

	updateProduct(id:number, product:Product): Observable<any> {
		return this.httpClient.put(AppConstants.MANAGEMENT_API + 'product/' + id, JSON.stringify(product), AppConstants.httpOptions)
	}

	deleteProduct(id:number){
		return this.httpClient.delete(AppConstants.MANAGEMENT_API + 'product/' + id)
	}

  //-------------------- Category--------------------//

  getAllCategory(request: any): Observable<any> {
    const params = request;
    return this.httpClient.get(AppConstants.MANAGEMENT_API + 'category',{params});
  }

  getListDeleted(request: any): Observable<any> {
    const params = request;
    return this.httpClient.get(AppConstants.MANAGEMENT_API + 'category/deleted', {params});
  }

  getImage(name: any): Observable<any> {
    return this.httpClient.get(AppConstants.MANAGEMENT_API + 'images/' + name)
  }

  createCategory(request: any): Observable<any> {
    return this.httpClient.post(AppConstants.MANAGEMENT_API + 'category', request, {
      responseType: 'json',
    });
  }

  findCategory(id:number): Observable<any> {
    return this.httpClient.get<Category>(AppConstants.MANAGEMENT_API + 'category/' + id)
  }

  updateCategory(id:number, request: any): Observable<any> {
    return this.httpClient.put(AppConstants.MANAGEMENT_API + 'category/' + id, request, {
      reportProgress: true,
      responseType: 'json',
    });
  }

  deleteCategory(id:number){
    return this.httpClient.delete(AppConstants.MANAGEMENT_API + 'category/' + id)
  }

  //--------------------  Color--------------------//

  getAllColor(request: any): Observable<any> {
    const params = request;
    return this.httpClient.get(AppConstants.MANAGEMENT_API + 'color', {params});
  }

  createColor(color:any): Observable<any> {
    return this.httpClient.post(AppConstants.MANAGEMENT_API + 'color', JSON.stringify(color), AppConstants.httpOptions)
  }

  findColor(id:number): Observable<any> {
    return this.httpClient.get<Color>(AppConstants.MANAGEMENT_API + 'color/' + id)
  }

  updateColor(id:number, color:Color): Observable<any> {
    return this.httpClient.put(AppConstants.MANAGEMENT_API + 'color/' + id, JSON.stringify(color), AppConstants.httpOptions)
  }

  deleteColor(id:number){
    return this.httpClient.delete(AppConstants.MANAGEMENT_API + 'color/' + id)
  }

  //--------------------  Size--------------------//

  getAllSize(request: any): Observable<any> {
    const params = request;
    return this.httpClient.get(AppConstants.MANAGEMENT_API + 'size', {params});
  }

  createSize(size:any): Observable<any> {
    return this.httpClient.post(AppConstants.MANAGEMENT_API + 'size', JSON.stringify(size), AppConstants.httpOptions)
  }

  findSize(id:number): Observable<any> {
    return this.httpClient.get<Size>(AppConstants.MANAGEMENT_API + 'size/' + id)
  }

  updateSize(id:number, size:Size): Observable<any> {
    return this.httpClient.put(AppConstants.MANAGEMENT_API + 'size/' + id, JSON.stringify(size), AppConstants.httpOptions)
  }

  deleteSize(id:number){
    return this.httpClient.delete(AppConstants.MANAGEMENT_API + 'size/' + id)
  }
}
