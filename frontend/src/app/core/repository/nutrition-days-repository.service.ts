import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { NutritionDayLightDto, NutritionDayRichDto } from './products-repository.service';

@Injectable({
  providedIn: 'root'
})
export class NutritionDaysRepositoryService {
  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  /* ------------- GET Endpoints ---------------------- */

  getNutritionDays(): Observable<NutritionDayLightDto[]> {
    return this.httpClient.get<NutritionDayLightDto[]>(`${this.baseUrl}/nutritionDay`, { withCredentials: true })
  }

  getNutritionDay(id: string): Observable<NutritionDayRichDto> {
    return this.httpClient.get<NutritionDayRichDto>(`${this.baseUrl}/nutritionDay/${id}`, { withCredentials: true })
  }

  /* ------------- POST Endpoints ---------------------- */

  addNutritionDay(): Observable<NutritionDayLightDto[]> {
    return this.httpClient.post<NutritionDayLightDto[]>(`${this.baseUrl}/nutritionDay`, {}, { withCredentials: true })
  }

}