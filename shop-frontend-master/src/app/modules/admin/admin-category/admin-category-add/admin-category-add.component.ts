import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminCategoryService } from '../admin-category.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminMessageService } from '../../common/service/admin-message.service';

@Component({
  selector: 'app-admin-category-add',
  templateUrl: './admin-category-add.component.html',
  styleUrls: ['./admin-category-add.component.scss']
})
export class AdminCategoryAddComponent implements OnInit {

  categoryForm!: FormGroup;


  constructor(private formBuilder: FormBuilder,
    private adminCategoryService: AdminCategoryService,
    private router: Router, // potrzebny do zrobienia przekierowania
    private snackBar: MatSnackBar,
    private adminMessageService: AdminMessageService
    ){

  }
  ngOnInit(): void {
    // definicja formularza kategorii
    this.categoryForm = this.formBuilder.group({
      name: ["", [Validators.required, Validators.minLength(4)]],
      description: [""],
      slug: ["", [Validators.required, Validators.minLength(4)]]
    })
  }

  submit(){
    this.adminCategoryService.createCategory(this.categoryForm.value) // this.categoryForm.value - to co zawiera formularz
      .subscribe({
        next: category => {
          this.router.navigate(["/admin/categories"])
          .then(() => this.snackBar.open('Kategoria została dodana', '', {duration: 3000}))
        },
        error: err => {
          this.adminMessageService.addSpringErrors(err.error);
        }
      })
  }
}
