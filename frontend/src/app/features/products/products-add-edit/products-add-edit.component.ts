import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/navbar/navbar.component";
import { ProductsFormAddEditComponent } from "../../../shared/products-form-add-edit/products-form-add-edit.component";
import { ProductCommand, ProductDto, ProductsRepositoryService } from '../../../core/repository/products-repository.service';
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
export class ProductsAddEditComponent {

  isReadOnly: boolean = false;
  id: string | undefined = undefined;
  product: ProductDto | undefined = undefined;

  constructor(
    private productsRepositoryService: ProductsRepositoryService,
    private messageService: MessageService,
    private router: Router
  ) {
    const url = this.router.url;
    this.isReadOnly = url.split('/').filter(Boolean).pop() !== 'edit';
    if (!this.isReadOnly) {
      this.id = this.router.url.split('/').filter(Boolean).at(-2);
    }
  }

  async ngOnInit(): Promise<void> {
    if (!this.isReadOnly) {
      this.productsRepositoryService.getProductById(this.id!).subscribe(product => {
        this.product = product;
        console.log(product)
      })
    }
  }

  addOrEditProduct(event: ProductCommand): void {
    if (this.product) {
      this.updateProduct(event);
    } else {
      this.addProduct(event);
    }
  }

  /* ------------------ PRIVATE Functions -------------------- */

  private addProduct(event: ProductCommand): void {
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

  private updateProduct(event: ProductCommand): void {
    this.productsRepositoryService.updateProduct(this.product!.id, event).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Produit mise à jour', detail: 'Le produit a bien été mise à jour' });
        this.router.navigateByUrl('/products');
      },
      error: (error) => {
        console.log(error);
      }
    });
  }
}
