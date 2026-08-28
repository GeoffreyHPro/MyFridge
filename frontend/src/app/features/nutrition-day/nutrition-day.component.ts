import { Component } from '@angular/core';
import { NavbarComponent } from "../../shared/navbar/navbar.component";
import { TableModule } from "primeng/table";
import { NutritionDayLightDto } from '../../core/repository/products-repository.service';
import { RouterLink } from '@angular/router';
import { NutritionDaysRepositoryService } from '../../core/repository/nutrition-days-repository.service';

@Component({
  selector: 'app-nutrition-day',
  standalone: true,
  imports: [NavbarComponent, TableModule, RouterLink],
  templateUrl: './nutrition-day.component.html',
  styleUrl: './nutrition-day.component.css'
})
export class NutritionDayComponent {
  id: string | null = "";

  nutritionDays: NutritionDayLightDto[] = [];
  itemHeaders = ['name', 'quantity', 'calories', 'proteins', 'carbohydrates', 'lipids'];

  totalItemHeaders = ['calories', 'proteins', 'carbohydrates', 'lipids'];
  totalItemValues: number[] = []


  constructor(
    private nutritionDaysRepositoryService: NutritionDaysRepositoryService,
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
}
