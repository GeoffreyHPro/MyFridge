import { Component } from '@angular/core';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TabProductsComponent } from '../../shared/generic-tab/generic-tab.component';
import { IconFieldModule } from "primeng/iconfield";
import { ProductSearchService, ProductsFilterSearch } from '../../core/product-search.service';
import { Subscription } from 'rxjs';
import { UserService } from '../../core/user.service';
import { Router } from '@angular/router';
import { TableLazyLoadEvent } from 'primeng/table';

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
    private productSearchService: ProductSearchService,
    protected userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.productsStateSubscription = this.productSearchService.productsState$.subscribe(state => {
      this.productsState = state;
      this.inputSearchName = state.name;
    });

    this.productsStateSubscription.add(
    this.productSearchService.search(
      0,
      this.productsState.rows,
      this.productsState.name
    )
  );
  }

  onSearch(): void {
    this.productSearchService.search(
      0,
      this.productsState?.rows ?? 5,
      this.inputSearchName
    );
  }

  isActualSearch(event: any): boolean {
    const isCurrentSearch = this.productsState.page === event.first / event.rows && this.productsState.rows === event.rows && this.productsState.name === this.inputSearchName;
    const isData = this.productsState.products && this.productsState.products.length > 0;

    return isCurrentSearch && isData;
  }

  /**
   * Load and reload products table
   * @param event TableLazyLoadEvent
   */
  loadProductsLazy(event: TableLazyLoadEvent) {
    if (!event) return;

    const page = Math.floor((event.first ?? 0) / (event.rows ?? this.productsState?.rows ?? 5));
    const rows = event.rows ?? this.productsState?.rows ?? 5;

    this.productSearchService.search(page, rows, this.inputSearchName);
  }

  redirectToProductAdd(): void {
    this.router.navigate(["/products/add"]);
  }

  ngOnDestroy(): void {
    this.productsStateSubscription?.unsubscribe();
  }
}
