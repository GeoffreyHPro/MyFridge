import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { MealRichDto } from './products-repository.service';

@Injectable({
  providedIn: 'root'
})
export class MealsRepositoryService {
  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getMeal(id: string): Observable<MealRichDto> {
    return this.httpClient.get<MealRichDto>(`${this.baseUrl}/meal/${id}/full`, { withCredentials: true })
  }

  deleteMealItem(mealId: string, mealItemId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/meal/${mealId}/mealItem/${mealItemId}`, { withCredentials: true })
  }

  patchMealItem(mealId: string, mealItemId: string, quantity: number): Observable<void> {
    return this.httpClient.patch<void>(`${this.baseUrl}/meal/${mealId}/mealItem/${mealItemId}`, { quantity }, { withCredentials: true })
  }

}