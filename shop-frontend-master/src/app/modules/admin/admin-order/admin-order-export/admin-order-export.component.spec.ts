import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOrderExportComponent } from './admin-order-export.component';

describe('AdminOrderExportComponent', () => {
  let component: AdminOrderExportComponent;
  let fixture: ComponentFixture<AdminOrderExportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminOrderExportComponent]
    });
    fixture = TestBed.createComponent(AdminOrderExportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
