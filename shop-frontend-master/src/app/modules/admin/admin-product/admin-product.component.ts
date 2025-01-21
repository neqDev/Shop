import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AdminProduct } from './adminProduct';
import { AdminProductService } from './admin-product.service';
import { MatPaginator } from '@angular/material/paginator';
import { map, startWith, switchMap } from 'rxjs';
import { AdminConfirmDialogService } from '../common/service/admin-confirm-dialog.service';
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'app-admin-product',
  templateUrl: './admin-product.component.html',
  styleUrls: ['./admin-product.component.scss']
})
export class AdminProductComponent implements /*OnInit,*/ AfterViewInit{

  //dataSource: AdminProduct[] = []

  /*VievChild dodajem paginator do naszego komponentu,
  laczy komponent tabeli z komponentem paginacji*/
  @ViewChild(MatPaginator) paginator!: MatPaginator; 
  @ViewChild(MatTable) table!: MatTable<any> // refernecja do naszej tabeli 
 

  displayedColumns: string[] = ["image", "id", "name", "price", "actions"];
  totalElements: number = 0;
  data: AdminProduct[] = []; // zawiera liste naszych produtkow
  
  constructor(
    private adminProductService: AdminProductService,
    private dialogService: AdminConfirmDialogService){ }


  // ngOnInit(): void {
  //    this.getProduct();
  // }

  ngAfterViewInit(): void {
    this.paginator.page.pipe( // zwraca Observable<PageEvent> + dodatkowe operatory
      startWith({}), // {} pusty element, musi byc bo nam nie zadziala przy wejsciu na strone
      switchMap(() => { // zamieniamy Observable na Naszego z getProducts
        return this.adminProductService
        .getProducts(this.paginator.pageIndex, this.paginator.pageSize);
      })      
    ).subscribe(data => { // subscribe aby wszsytko nam zadziałało
      this.totalElements = data.totalElements
      this.data = data.content}); 
  }

  confirmDelete(element: AdminProduct){
    this.dialogService.openConfirmDialog("Czy na pewno chcesz usunąć ten produkt?")
    .afterClosed()
    .subscribe(result => {
      if(result){
        // delete
        this.adminProductService.delete(element.id)
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
  // getProduct(){
  //   this.adminProductService.getProducts(0,25)
  //   .subscribe(page => this.dataSource = page.content)
  // }
}
