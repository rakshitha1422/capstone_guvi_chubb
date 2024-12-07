import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerreportComponent } from './sellerreport.component';

describe('SellerreportComponent', () => {
  let component: SellerreportComponent;
  let fixture: ComponentFixture<SellerreportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellerreportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SellerreportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
