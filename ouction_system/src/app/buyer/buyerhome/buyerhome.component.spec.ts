import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyerhomeComponent } from './buyerhome.component';
import { ToastrModule } from 'ngx-toastr';

describe('BuyerhomeComponent', () => {
  let component: BuyerhomeComponent;
  let fixture: ComponentFixture<BuyerhomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyerhomeComponent,ToastrModule.forRoot()]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BuyerhomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
