import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../../_services/product.service";
import {Category} from "../../category/category";
import {Size} from "../../size/size";
import {Color} from "../../color/color";

@Component({
  selector: 'app-product-create-edit',
  templateUrl: './product-create-edit.component.html',
  styleUrls: ['./product-create-edit.component.scss'],
})
export class ProductCreateEditComponent implements OnInit {

  @ViewChild("inputImage", {static: false}) inputImage: any;
  id: any;
  productForm: FormGroup = new FormGroup({
    productName: new FormControl(),
    productDescription: new FormControl(),
    productImages: new FormControl(),
    productQuantity: new FormControl(),
    price: new FormControl(),
    productCreatedDate: new FormControl(),
    productUpdateDate: new FormControl(),
  });

  numberPattern = '/^[0-9]+$/';
  category: Category[] = [];
  size: Size[] = [];
  color: Color[] = [];

  imageName: string = '';
  imageDataUrl: any = null;
  errorMessage: any;

  constructor(
    public productService: ProductService,
    private router: Router,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute) {
    this.id = this.route.snapshot.paramMap.get('id');
    console.log(this.id);
  }


  ngOnInit(): void {
    this.createForm();
    if (this.id) {
      this.productService.findProduct(this.id).subscribe(x => this.productForm.patchValue(x));
    }
    this.imageName = "upload an image";
  }

  // slug(productName: string) {
  //   const productSlug = this.slugPipe.transform(productName);
  // }

  onUploadImageButtonClick() {
    this.inputImage.nativeElement.click();
  }

  onFileChange() {
    const target = this.inputImage.nativeElement;
    console.log('target = ', target.files);
    if (this.checkImages(target.files[0])) {
      if (target.files && target.files.length) {
        const [file] = target.files;
        this.productForm.patchValue({
          image: file
        });
        this.imageName = target.files[0].name;
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = _event => {
          this.imageDataUrl = reader.result;
        };
      }
    } else {
      console.log('ggg')
    }

  }

  checkImages(data: any) {
    let type: string = data.type;
    let arr = type.split('/')
    return arr[0] === 'image';
  }

  createForm() {
    this.productForm = this.formBuilder.group({
      productName: ['',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20),
        ]
      ],
      productDescription: ['',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ]
      ],
      // category: ['', Validators.required],
      // size: ['', Validators.required],
      // color: ['', Validators.required],
      productQuantity: ['',
        [
          Validators.required,
          Validators.pattern(this.numberPattern)
        ]
      ],
      price: ['',
        [
          Validators.required,
          Validators.pattern(this.numberPattern)
        ]
      ],
      productImages: [null],
      productCreatedDate: [],
      productUpdateDate: []
    });
  }

  get formControl() {
    return this.productForm.controls;
  }

  onSubmit() {

    console.log(this.productForm.value);

    const response = this.id ? this.productService.updateProduct(this.id, this.productForm.value) : this.productService.createProduct(this.productForm.value);

    response.subscribe(
      (data: any) => {
        console.log('Product created / updated successfully!');
        this.router.navigateByUrl('product/list').then();
      }
      ,
      (err: any) => {
        this.errorMessage = err.error.message;
      }
    );
  }

}
