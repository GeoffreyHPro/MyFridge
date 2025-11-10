import { Component } from '@angular/core';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TabProductsComponent } from '../../shared/generic-tab/generic-tab.component';
import { IconFieldModule } from "primeng/iconfield";
import { ProductSearchService, ProductsFilterSearch } from '../../core/product-search.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [NavbarComponent, FormsModule, ReactiveFormsModule, TabProductsComponent, IconFieldModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent {
  /* Observable */
  private productsStateSubscription?: Subscription;
  /* State */
  productsState!: ProductsFilterSearch;

  /* Component */
  inputSearchName: string = '';

  constructor(
    private productSearchService: ProductSearchService
  ) { }

  ngOnInit(): void {
    this.productsStateSubscription = this.productSearchService.productsState$.subscribe(state => {
      this.productsState = state;
      this.inputSearchName = state.name;
    });
  }

  onSearch(): void {
    if (this.isActualSearch(event)) {
      return;
    }

    this.productSearchService.search(
      0,
      this.productsState?.rows,
      this.inputSearchName
    );
  }

  isActualSearch(event: any): boolean {
    const isCurrentSearch = this.productsState.page === event.first / event.rows && this.productsState.rows === event.rows && this.productsState.name === this.inputSearchName;
    const isData = this.productsState.products && this.productsState.products.length > 0;

    return isCurrentSearch && isData;
  }

  loadProductsLazy(event: any) {
    if (!event) return;

    if (this.isActualSearch(event)) {
      return;
    }

    this.productSearchService.search(event.first / event.rows, event.rows, this.inputSearchName);
  }

  onLogout(): void { }

  ngOnDestroy(): void {
    this.productsStateSubscription?.unsubscribe();
  }
}
