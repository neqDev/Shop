import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminMessageService } from '../../common/service/admin-message.service';
import { ActivatedRoute } from '@angular/router';
import { AdminCategoryService } from '../admin-category.service';
import { AdminCategory } from '../model/adminCategory';

@Component({
  selector: 'app-admin-category-update',
  templateUrl: './admin-category-update.component.html',
  styleUrls: ['./admin-category-update.component.scss']
})
export class AdminCategoryUpdateComponent implements OnInit{

  categoryForm!: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private adminMessageService: AdminMessageService,
    private adminCategoryService: AdminCategoryService,
    private route: ActivatedRoute // do pobrania parametrów z aktywnej sciezki
    ){
    

  }

  ngOnInit(): void {
        // definicja formularza kategorii
        this.categoryForm = this.formBuilder.group({
          name: ["", [Validators.required, Validators.minLength(4)]],
          description: [""],
          slug: ["", [Validators.required, Validators.minLength(4)]]
        });
        this.getCategory();
  }

  getCategory(){
    this.adminCategoryService.getCategory(Number(this.route.snapshot.params['id']))
    .subscribe(category => this.mapToFormValue(category)); // ustawia wartosci w formularzu z pobranej kategori
  }

  submit(){
    this.adminCategoryService.saveCategory(Number(this.route.snapshot.params['id']), this.categoryForm.value)
    .subscribe({
      next: category => {
        this.mapToFormValue(category);
        this.snackBar.open("Kategoria została zapisana", "", {duration: 3000});
      },
      error: err =>{
        this.adminMessageService.addSpringErrors(err.error);
      }
    })
  }
  private mapToFormValue(category: AdminCategory) {
    this.categoryForm.setValue({ // ustawia wartosci w formularzu z pobranej kategori
      name: category.name,
      description: category.description,
      slug: category.slug
    });
  }
}
