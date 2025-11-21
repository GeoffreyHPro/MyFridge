import { Injectable } from '@angular/core';
import { Product, ProductsRepositoryService } from './repository/products-repository.service';
import { BehaviorSubject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductSearchService {
  private productsState = new BehaviorSubject<ProductsFilterSearch>({
    name: '',
    products: [],
    productsHeaders: ['ean', 'name', 'detail'],
    rows: 5,
    page: 0,
    totalRecords: 0
  });

  productsState$ = this.productsState.asObservable();

  constructor(private productsRepositoryService: ProductsRepositoryService) { }

  setSearchName(name: string): void {
    const current = this.productsState.value;
    this.productsState.next({ ...current, name });
  }

  search(page: number, rows: number, name: string): void {
    this.productsRepositoryService.getProducts(page, rows, name)
      .pipe(
        tap((response) => {
          this.productsState.next({
            name: name,
            products: response.content,
            productsHeaders: this.productsState.value.productsHeaders,
            rows,
            page,
            totalRecords: response.totalElements
          });
        })
      ).subscribe();
  }
}

export interface ProductsFilterSearch {
  name: string;
  products: Product[];
  productsHeaders: string[];
  rows: number;
  page: number;
  totalRecords: number;
}
