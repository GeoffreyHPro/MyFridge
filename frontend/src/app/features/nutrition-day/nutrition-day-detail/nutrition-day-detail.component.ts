import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/navbar/navbar.component";
import { TableModule } from "primeng/table";
import { NutritionDayRichDto } from '../../../core/repository/products-repository.service';
import { GenericOrderedTabComponent } from "../../../shared/generic-ordered-tab/generic-ordered-tab.component";
import { ActivatedRoute } from '@angular/router';
import { NutritionDaysRepositoryService } from '../../../core/repository/nutrition-days-repository.service';
import { MealsRepositoryService } from '../../../core/repository/meals-repository.service';

@Component({
  selector: 'app-nutrition-day',
  standalone: true,
  imports: [NavbarComponent, TableModule, GenericOrderedTabComponent],
  templateUrl: './nutrition-day-detail.component.html',
  styleUrl: './nutrition-day-detail.component.css'
})
export class NutritionDayDetailComponent {
  id: string | null = "";

  nutritionDay!: NutritionDayRichDto;
  itemHeaders = ['name', 'quantity', 'calories', 'proteins', 'carbohydrates', 'lipids'];

  totalItemHeaders = ['calories', 'proteins', 'carbohydrates', 'lipids'];
  totalItemValues: number[] = []


  constructor(
    private nutritionDaysRepositoryService: NutritionDaysRepositoryService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');

    this.nutritionDaysRepositoryService.getNutritionDay(this.id!).subscribe(nutritionDay => {
      this.nutritionDay = nutritionDay;
      this.totalItemValues = [nutritionDay.calories, nutritionDay.proteins, nutritionDay.carbohydrates, nutritionDay.lipids];
    })
  }

  refreshMeal(): void {
    this.nutritionDaysRepositoryService.getNutritionDay(this.id!).subscribe(nutritionDay => {
      this.nutritionDay = nutritionDay;
      this.totalItemValues = [nutritionDay.calories, nutritionDay.proteins, nutritionDay.carbohydrates, nutritionDay.lipids];
    })
  }
}
