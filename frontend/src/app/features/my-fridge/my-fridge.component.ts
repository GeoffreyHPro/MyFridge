import { Component } from '@angular/core';
import { NavbarComponent } from "../../shared/navbar/navbar.component";

@Component({
  selector: 'app-my-fridge',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './my-fridge.component.html',
  styleUrl: './my-fridge.component.css'
})
export class MyFridgeComponent {
onLogout() {
throw new Error('Method not implemented.');
}

}
