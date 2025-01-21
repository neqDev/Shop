import { Component, OnInit, ViewChild } from '@angular/core';
import { AdminCategoryNameDto } from '../common/dto/adminCategoryNameDto';
import { AdminCategoryService } from './admin-category.service';
import { AdminConfirmDialogService } from '../common/service/admin-confirm-dialog.service';
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'app-admin-category',
  templateUrl: './admin-category.component.html',
  styleUrls: ['./admin-category.component.scss']
})
export class AdminCategoryComponent implements OnInit{

  @ViewChild(MatTable) table!: MatTable<any> // refernecja do naszej tabeli 

  
  displayedColumns: string[] = ["id", "name", "actions"]; // kolumny widoczne
  data: Array<AdminCategoryNameDto> = []; // pusta tablica kategorii

  constructor(private adminCategoryService: AdminCategoryService,
    private dialogService: AdminConfirmDialogService
    ){

  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(){
    this.adminCategoryService.getCategories()
    .subscribe(categories => this.data = categories);
  }
  confirmDelete(element: AdminCategoryNameDto){
    this.dialogService.openConfirmDialog("Czy na pewno chcesz usunąć kategorię?")
    .afterClosed()
    .subscribe(result => {
      if(result){
        // delete
        this.adminCategoryService.delete(element.id)
        .subscribe(() => {
          this.data.forEach((value, index) => {
              if(element ==  value){
                this.data.splice(index,1); // ilosc elemetow do usuniecia
                this.table.renderRows(); // po usunieciu musimt zrednerowac nasza tablice (odswiezenie listy)
              }
          })
        })
      }
    });
  }

}
