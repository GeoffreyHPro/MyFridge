import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductsRepositoryService {
  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  /* ------------------ GET Endpoints ------------------------ */

  getProducts(page: number, size: number, name: string): Observable<Page<Product>> {
    return this.httpClient.get<Page<Product>>(`${this.baseUrl}/product?page=${page}&size=${size}&name=${name}`, { withCredentials: true })
  }
  
  getAllProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseUrl}/product/all`, { withCredentials: true })
  }

  getProductByEan(ean: string): Observable<Product> {
    return this.httpClient.get<Product>(`${this.baseUrl}/product/${ean}`, { withCredentials: true })
  }

  /* ------------------ POST Endpoints ------------------------ */

  addProduct(product: ProductCommand): Observable<void> {
    return this.httpClient.post<void>(`${this.baseUrl}/product`, product, { withCredentials: true });
  }

  /* ------------------ DELETE Endpoints ------------------------ */

  deleteProduct(id: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/product/${id}`, { withCredentials: true });
  }
}

export interface Page<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface ProductCommand {
  id: string;
  ean: string;
  name: string;
  detail: string;
  calories: string;
  proteins: string;
  carboHydrates: string;
  lipids: string;
  status: string;
}

export interface Product {
  id: string;
  ean: string;
  name: string;
  detail: string;
  status: string;
}

export interface MealRichDto {
  id: string,
  calories: number,
  lipids: number,
  proteins: number,
  carbohydrates: number,
  mealItems: MealItemRichDto[]
}

export interface NutritionDayLightDto {
  id: string
}

export interface NutritionDayRichDto {
  id: string,
  breakfast: MealRichDto,
  morningSnack: MealRichDto,
  lunch: MealRichDto,
  afternoonSnack: MealRichDto,
  dinner: MealRichDto,
  calories: number,
  proteins: number,
  carbohydrates: number,
  lipids: number
}

export interface MealItemRichDto {
  id: string,
  name: string,
  quantity: number,
  calories: number,
  lipids: number,
  proteins: number,
  carbohydrates: number
}

export interface ProductDto {
  id: string;
  ean: string;
  name: string;
  detail: string;
  calories: string;
  proteins: string;
  carboHydrates: string;
  lipids: string;
  status: string;
}