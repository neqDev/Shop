import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminProductUpdateService } from './admin-product-update.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminProductUpdate } from '../model/adminProductUpdate';
import { AdminMessageService } from '../../common/service/admin-message.service';
import { AdminProductImageService } from '../admin-product-image.service';

@Component({
  selector: 'app-admin-product-update',
  templateUrl: './admin-product-update.component.html',
  styleUrls: ['./admin-product-update.component.scss']
})
export class AdminProductUpdateComponent implements OnInit {

  product!: AdminProductUpdate;
  productForm!: FormGroup; // definicja dla formularza
  requiredFileTypes = "image/jpeg, image/png";
  imageForm!: FormGroup; // definicja dla formularza z ladowaniem zdjecia
  image: string | null = null;

  constructor(
    private router: ActivatedRoute,// pozawala na dostep do paramterow URL
    private adminProductUpdateService: AdminProductUpdateService,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private adminMessageService: AdminMessageService,
    private adminProductImage: AdminProductImageService) {

  }

  ngOnInit(): void {
    this.getProduct();
    this.productForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(4)]],
      categoryId: ['', [Validators.required]],
      description: ['', [Validators.required, Validators.minLength(4)]],
      fullDescription: [''],
      price: ['', [Validators.required, Validators.min(0)]],
      currency: ['PLN', [Validators.required]],
      slug: ['', [Validators.required, Validators.minLength(4)]]
    })

    this.imageForm = this.formBuilder.group({
      file: [''] // domyslna wartosc jest pusta
    })
  }

  getProduct() {
    let id = Number(this.router.snapshot.params['id']) // pobieranie id z URL(id produktu)
    this.adminProductUpdateService.getProduct(id)
      .subscribe(product => this.mapFormValues(product));
  }

  submit() { // metoda wywolana w formularzu (submit)="submit"
    let id = Number(this.router.snapshot.params['id']); // pobieranie id z URL(id produktu)
    // this.productForm.value tak mozemy przekazac gdy obiekt folumarza = obiektowi dto, musi byc 1 do 1
    this.adminProductUpdateService.saveProduct(id, { // przemapowanie
      name: this.productForm.get('name')?.value,
      description: this.productForm.get('description')?.value,
      fullDescription: this.productForm.get('fullDescription')?.value,
      categoryId: this.productForm.get('categoryId')?.value, // formControlName z formularza (komponentu)
      price: this.productForm.get('price')?.value,
      currency: this.productForm.get('currency')?.value,
      slug: this.productForm.get('slug')?.value,
      image: this.image
    } as AdminProductUpdate).subscribe({ // rzutowanie w typescript
        next: product => {
        this.mapFormValues(product); // podmiana danych w formularu po zapisie
        this.snackBar.open("Produkt został zapisany", '', {duration: 3000})
      },
      error: err => this.adminMessageService.addSpringErrors(err.error) // subscribe z dwoma paramterami w tym error
    });
  }

  uploadFile(){
    let formData = new FormData();
    formData.append('file', this.imageForm.get('file')?.value) //z formularza odczytujemy nazwe
    this.adminProductImage.uploadImage(formData)
    .subscribe(result => this.image = result.filename);
  }

  onFileChange(event: any){
    if(event.target.files.length > 0){
      this.imageForm.patchValue({
        file: event.target.files[0] // z pola event wezmiemy nazwe naszego pliku 
      });
    }
  }
  // podmieniamy dane w formularzu po zapisie w backendzie
  private mapFormValues(product: AdminProductUpdate): void {
      this.productForm.setValue({
      name: product.name,
      categoryId: product.categoryId,
      description: product.description,
      fullDescription: product.fullDescription,
      price: product.price,
      currency: product.currency,
      slug: product.slug
    });
    this.image = product.image
  }
}

