import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/navbar/navbar.component";
import { ProductsFormAddEditComponent } from "../../../shared/products-form-add-edit/products-form-add-edit.component";
import { ProductsActionService } from '../../../core/action/products-action.service';
import { Product, ProductsRepositoryService } from '../../../core/repository/products-repository.service';
import { BackButtonComponent } from "../../../shared/back-button/back-button.component";
import { ToastModule } from "primeng/toast";
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products-add-edit',
  standalone: true,
  imports: [NavbarComponent, ProductsFormAddEditComponent, BackButtonComponent, ToastModule],
  templateUrl: './products-add-edit.component.html',
  styleUrl: './products-add-edit.component.css'
})
export class ProductsAddEditComponent{

  constructor(
    private productsActionService: ProductsActionService,
    private productsRepositoryService: ProductsRepositoryService,
    private messageService: MessageService,
    private router: Router
  ) { 
  }

  addProduct(event: Product): void {

    this.productsRepositoryService.addProduct(event).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Produit créé', detail: 'Le produit a bien été créé' });
        this.router.navigateByUrl('/products');
      },
      error: (error) => {
        if (error.status === 409) {
          this.messageService.add({ severity: 'error', summary: 'Produit déjà créé', detail: "Ce produit existe déjà" });
        } else {
          this.messageService.add({ severity: 'error', summary: 'Produit non créé', detail: "Le produit n'a pas été créé" });
        }
      }
    });
  }
}
