import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { KnobModule } from 'primeng/knob';

@Component({
  selector: 'app-nutrition-knob',
  standalone: true,
  imports: [KnobModule, FormsModule],
  templateUrl: './nutrition-knob.component.html',
  styleUrl: './nutrition-knob.component.css'
})
export class NutritionKnobComponent {
  @Input() value: number = 0;
  @Input() max!: number;
  @Input() goal!: number;
  @Input() label = '';
  @Input() strokeWidth: number = 2; 
  @Input() size!: number;

  @Input() colorFunction?: (value: number, goal: number, label: string) => string;


  private readonly labels: Record<string, string> = {
    calories: 'Calories',
    proteins: 'Protéines',
    carbohydrates: 'Glucides',
    lipids: 'Lipides'
  };

  get displayLabel(): string {
    return this.labels[this.label] ?? this.label;
  }

  get color(): string {
        return this.colorFunction
            ? this.colorFunction(this.value, this.goal, this.label)
            : 'gray';
    }

}
