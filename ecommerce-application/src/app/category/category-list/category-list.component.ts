import {Component, OnInit} from '@angular/core';
import {Category} from "../category";
import {ProductService} from "../../_services/product.service";
import {Observable} from "rxjs";
import {AppConstants} from "../../common/app.constants";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent implements OnInit {

  category: Category[] = [];
  selectedFiles?: FileList;
  progressInfos: any[] = [];
  message: string[] = [];
  previews: string[] = [];
  fileInfos?: Observable<any>;
  closeResult: string = '';
  // url: string = '';

  constructor(public productService: ProductService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getCategory({ page: "0", size: "5" });
  }

  private getCategory(request: any) {
    this.productService.getAllCategory(request).subscribe({
      next: (data: any) => {
        this.category = data['content'];
        this.category.forEach((_item: any) => {
          _item['url'] = AppConstants.IMAGE_API + _item.categoryImageDB?.name;
        })
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    });
  }

  openModal(content: any, id: number) {
    this.modalService.open(content, { ariaLabelledBy: 'categoryDelete' }).result.then((result: any) => {
      this.closeResult = `Đóng khi: ${result}`;
      if (result === 'yes') {
        this.deleteCategory(id);
      }
    }, (reason) => {
      this.closeResult = `Thoát ${CategoryListComponent.getDismissReason(reason)}`;
    });
  }

  private static getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'bằng cách nhấn ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'bằng cách nhấp vào a backdrop';
    } else {
      return `bằng: ${reason}`;
    }
  }

  deleteCategory(id:number){
    this.productService.deleteCategory(id)
      .subscribe((data: any) => {
          this.category = this.category.filter(item => item.id !== id);
          console.log('Product deleted successfully!');
        }
        , (error: any) => {
          console.log(error.error.message);
        }
      );
  }

}
