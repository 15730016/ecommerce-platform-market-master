import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../_services/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Category} from "../category";

@Component({
  selector: 'app-category-detail',
  templateUrl: './category-detail.component.html',
  styleUrls: ['./category-detail.component.scss']
})
export class CategoryDetailComponent implements OnInit {

  id: number = 0;
  category?: Category;

  constructor(
    public productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.productService.findCategory(this.id).subscribe((data: Category)=>{
      this.category = data;
    });
  }

}
