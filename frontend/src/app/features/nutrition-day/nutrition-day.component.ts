import { Component } from '@angular/core';
import { NavbarComponent } from "../../shared/navbar/navbar.component";
import { TableModule } from "primeng/table";
import { NutritionDayLightDto, NutritionDayRichDto } from '../../core/repository/products-repository.service';
import { Router, RouterLink } from '@angular/router';
import { NutritionDaysRepositoryService } from '../../core/repository/nutrition-days-repository.service';
import { Button } from "primeng/button";
import { NutritionDayMealTabAddEditRemove } from './nutrition-day-detail/nutrition-day-meal-tab-add-edit-remove/nutrition-day-meal-tab-add-edit-remove';

@Component({
  selector: 'app-nutrition-day',
  standalone: true,
  imports: [NavbarComponent, TableModule, RouterLink, Button],
  templateUrl: './nutrition-day.component.html',
  styleUrl: './nutrition-day.component.css'
})
export class NutritionDayComponent {
  id: string | null = "";

  nutritionDays: NutritionDayLightDto[] = [];
  itemHeaders = ['name', 'quantity', 'calories', 'proteins', 'carbohydrates', 'lipids'];

  totalItemHeaders = ['calories', 'proteins', 'carbohydrates', 'lipids'];
  totalItemValues: number[] = []

  selectedNutritionDayId: string = "";
  currentNutritionDay: NutritionDayRichDto | null = null;

  constructor(
    private nutritionDaysRepositoryService: NutritionDaysRepositoryService,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.nutritionDaysRepositoryService.getNutritionDays().subscribe(nutritionDays => {
      this.nutritionDays = nutritionDays;
    });
  }

  addNutritionDay(): void {
    this.nutritionDaysRepositoryService.addNutritionDay().subscribe(nutritionDays => {
      this.nutritionDays = nutritionDays;
    });
  }

  selectNutritionDay(id: string): void {
    this.nutritionDaysRepositoryService.getNutritionDay(id).subscribe(nutritionDay => {
      this.selectedNutritionDayId = id;
      this.currentNutritionDay = nutritionDay;
    });
  }

  redirectToNutritionDayDetails(): void {
    this.router.navigate(['/nutritionDay', this.currentNutritionDay?.id])
  }
}
