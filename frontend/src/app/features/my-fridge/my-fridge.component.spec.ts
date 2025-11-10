import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFridgeComponent } from './my-fridge.component';

describe('MyFridgeComponent', () => {
  let component: MyFridgeComponent;
  let fixture: ComponentFixture<MyFridgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyFridgeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyFridgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
