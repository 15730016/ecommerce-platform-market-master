import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProductService} from "../../_services/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CategoryImageDB, ProgressInfo} from "../category";
import {AppConstants} from "../../common/app.constants";

@Component({
  selector: 'app-category-create-edit',
  templateUrl: './category-create-edit.component.html',
  styleUrls: ['./category-create-edit.component.scss']
})
export class CategoryCreateEditComponent implements OnInit {

  categoryId: any;
  categoryImageDBId: CategoryImageDB[] = [];
  categoryForm: FormGroup = new FormGroup({
    categoryName: new FormControl(),
    categoryImage: new FormControl()
  })
  submitted = false;

  selectedFiles?: FileList;
  progressInfos: ProgressInfo[] = [];
  message: string[] = [];
  previews: string = './assets/img/no-image.png';
  constructor(
    public productService: ProductService,
    private router: Router,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute) {
    this.categoryId = this.route.snapshot.paramMap.get('id');
  }

  selectFiles(event: any): void {
    this.message = [];
    this.selectedFiles = event.target.files;
    if (this.selectedFiles && this.selectedFiles[0]) {
      // const numberOfFiles = this.selectedFiles.length;
      const reader = new FileReader();
      reader.onload = (e: any) => {
        console.log(e.target.result);
        this.previews = e.target.result;
      };
      reader.readAsDataURL(this.selectedFiles[0]);
      console.log('imageId = ', this.categoryImageDBId)

      // for (let i = 0; i < numberOfFiles; i++) {
      //
      // }
    }
  }

  OnSubmit(): void {
    this.message = [];
    if(this.categoryForm.valid) {
      if (this.selectedFiles) {
        this.upload(this.selectedFiles[0])
        // for (let i = 0; i < this.selectedFiles.length; i++) {
        //   this.upload(i, this.selectedFiles[i]);
        // }
      }
    }
  }

  upload(file: any): void {
    const formData: any = new FormData();
    formData.append('image', file, file.name);
    formData.append('categoryName', this.categoryForm.value.categoryName)
    formData.append('categoryImageDB', this.categoryImageDBId)
    if (file) {
      if (this.categoryId){
        this.productService.updateCategory(this.categoryId, formData).subscribe({
          next: (event: any) => {
            console.log();
            const msg = 'Đã tải hình ảnh lên thành công: ' + file.name;
            this.message.push(msg);
            this.router.navigateByUrl('category/list').then();
          },
          error: (err: any) => {
            const msg = 'Tải hình ảnh lên thất bại: ' + file.name;
            this.message.push(msg);
          }
        })
      } else {
        this.productService.createCategory(formData).subscribe({
          next: (event: any) => {
            const msg = 'Đã tải hình ảnh lên thành công: ' + file.name;
            this.message.push(msg);
            this.router.navigateByUrl('category/list').then();
          },
          error: (err: any) => {
            const msg = 'Tải hình ảnh lên thất bại: ' + file.name;
            this.message.push(msg);
          }});
      }
    }
  }

  ngOnInit(): void {
    this.createForm();
    if (this.categoryId) {
      this.productService.findCategory(this.categoryId).subscribe(x => {
        if (x.categoryImageDB.id != null) {
          this.categoryImageDBId = x.categoryImageDB.id;
          this.categoryForm.patchValue(x)
          this.previews = AppConstants.IMAGE_API + x.categoryImageDB.name;
        }
      });
    }
  }

  createForm(): void {
    this.categoryForm = this.formBuilder.group({
      categoryName: ['', Validators.required],
      categoryImage: [null]
    })
  }

  get formControl() {
    return this.categoryForm.controls;
  }

}
