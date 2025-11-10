import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductsRepositoryService {
  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getProducts(page: number, size: number, name: string): Observable<Page<Product>> {
    return this.httpClient.get<Page<Product>>(`${this.baseUrl}/product?page=${page}&size=${size}&name=${name}`, { withCredentials: true })
  }
}

export interface Page<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface Product {
  id: string;
  ean: string;
  name: string;
  detail: string;
}