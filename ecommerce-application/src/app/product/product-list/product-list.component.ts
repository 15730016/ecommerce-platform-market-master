import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../_services/product.service";
import {Product} from "../product";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  product: Product[] = [];

  constructor(public productService: ProductService) {
  }

  ngOnInit(): void {
    this.getProducts({ page: "0", size: "5" });
  }

  private getProducts(request: any) {
    this.productService.getAllProduct(request)
      .subscribe((data: any) => {
          this.product = data['content'];
        }
        , (error: any) => {
          console.log(error.error.message);
        }
      );
  }

  deleteProduct(id:number){
    this.productService.deleteProduct(id)
      .subscribe((data: any) => {
          this.product = this.product.filter(item => item.id !== id);
          console.log('Product deleted successfully!');
        }
        , (error: any) => {
          console.log(error.error.message);
        }
      );
  }

}
