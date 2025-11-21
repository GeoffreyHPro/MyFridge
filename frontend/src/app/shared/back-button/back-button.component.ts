import { Component } from '@angular/core';
import { ButtonDirective } from "primeng/button";
import { Location } from '@angular/common';

@Component({
  selector: 'app-back-button',
  standalone: true,
  imports: [ButtonDirective],
  templateUrl: './back-button.component.html',
  styleUrl: './back-button.component.css'
})
export class BackButtonComponent {
  constructor(private location: Location) { }

  goBack() {
    this.location.back();
  }
}
