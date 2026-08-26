import { Component, Input } from '@angular/core';
import { TableModule } from "primeng/table";
import { UpperCasePipe } from '@angular/common';
import { MenuModule } from "primeng/menu";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-generic-ordered-tab',
  standalone: true,
  imports: [TableModule, UpperCasePipe, MenuModule, FormsModule],
  templateUrl: './generic-ordered-tab.component.html',
  styleUrl: './generic-ordered-tab.component.css'
})
export class GenericOrderedTabComponent {
  @Input() items: any[] = [];
  @Input() itemHeaders: string[] = [];
  @Input() totalItems!: any[];

  newRow: any = {};

  ngOnInit(): void {
    this.resetNewRow();
  }

  resetNewRow(): void {
    this.newRow = {};

    this.itemHeaders.forEach(header => {
      this.newRow[header] = '';
    });
  }

  addRow(): void {

    this.items = [
      ...this.items,
      { ...this.newRow }
    ];

    this.resetNewRow();
  }
}
