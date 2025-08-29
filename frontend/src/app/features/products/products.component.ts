import { Component } from '@angular/core';
import { UserService } from '../../core/user.service';
import { NavbarComponent } from '../../shared/navbar/navbar.component';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUserInfos().subscribe();
  }

  onLogout(): void {

  }
}
