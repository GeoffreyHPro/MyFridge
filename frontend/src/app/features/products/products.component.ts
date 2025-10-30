import { Component } from '@angular/core';
import { UserService } from '../../core/user.service';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [NavbarComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {

  constructor(private userService: UserService) { 
  }

  ngOnInit(): void {
    this.userService.getOwnUserInfos().subscribe();
  }

  onLogout(): void {

  }
}
