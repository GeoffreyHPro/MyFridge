import { Component } from '@angular/core';
import { NavbarComponent } from "../../shared/navbar/navbar.component";
import { TableModule } from "primeng/table";
import { NutritionDayRichDto } from '../../core/repository/products-repository.service';
import { Router, RouterLink } from '@angular/router';
import { NutritionDaysRepositoryService } from '../../core/repository/nutrition-days-repository.service';
import { Button } from "primeng/button";
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-nutrition-day',
  standalone: true,
  imports: [NavbarComponent, TableModule, RouterLink, Button, CalendarModule, FormsModule],
  templateUrl: './nutrition-day.component.html',
  styleUrl: './nutrition-day.component.scss'
})
export class NutritionDayComponent {
  id: string | null = "";

  nutritionDay: NutritionDayRichDto | null = null;
  itemHeaders = ['name', 'quantity', 'calories', 'proteins', 'carbohydrates', 'lipids'];

  totalItemHeaders = ['calories', 'proteins', 'carbohydrates', 'lipids'];
  totalItemValues: number[] = []

  selectedNutritionDayId: string = "";
  currentNutritionDay: NutritionDayRichDto | null = null;

  selectedDate: Date | null = null;

  constructor(
    private nutritionDaysRepositoryService: NutritionDaysRepositoryService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onDateSelect(): void {
    this.filterByDate();
  }

  private filterByDate(): void {
  if (!this.selectedDate) {
    this.currentNutritionDay = null;
    return;
  }

  const selected = this.formatDate(this.selectedDate);


  this.nutritionDaysRepositoryService
    .getNutritionDaysByDate(selected)
    .subscribe(nutritionDay => {
      this.nutritionDay = nutritionDay;
      this.currentNutritionDay = nutritionDay;
    });
}

  private formatDate(date: Date): string {
    const offset = date.getTimezoneOffset();
    const local = new Date(date.getTime() - offset * 60000);
    return local.toISOString().slice(0, 10);
  }

  addNutritionDay(): void {
    if (!this.selectedDate) {
      return;
    }

    const date = this.formatDate(this.selectedDate);
    this.nutritionDaysRepositoryService.addNutritionDay(date).subscribe(nutritionDay => {
      this.currentNutritionDay = nutritionDay;
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
