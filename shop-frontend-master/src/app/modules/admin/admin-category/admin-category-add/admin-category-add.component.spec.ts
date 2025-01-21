import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCategoryAddComponent } from './admin-category-add.component';

describe('AdminCategoryAddComponent', () => {
  let component: AdminCategoryAddComponent;
  let fixture: ComponentFixture<AdminCategoryAddComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminCategoryAddComponent]
    });
    fixture = TestBed.createComponent(AdminCategoryAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
