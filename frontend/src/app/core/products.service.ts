import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Product } from '../features/products/tab-products/tab-products.component';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getProducts(page: number, size: number): Observable<Page<Product>> {
    return this.httpClient.get<Page<Product>>(`${this.baseUrl}/product?page=${page}&size=${size}`, { withCredentials: true })
  }
}

export interface Page<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}