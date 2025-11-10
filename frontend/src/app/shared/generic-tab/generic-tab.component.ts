import { Component, EventEmitter, Input, Output, ViewEncapsulation } from '@angular/core';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import { IconFieldModule } from 'primeng/iconfield';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';
import { TableLazyLoadEvent } from 'primeng/table';
import { UpperCasePipe } from '@angular/common';

@Component({
  selector: 'app-generic-tab',
  standalone: true,
  imports: [TableModule, HttpClientModule, InputTextModule, IconFieldModule, InputIconModule, TagModule, UpperCasePipe],
  templateUrl: './generic-tab.component.html',
  styleUrl: './generic-tab.component.css',
  encapsulation: ViewEncapsulation.None
})
export class TabProductsComponent {
  @Input() itemList: any[] = [];
  @Input() itemHeaders: any[] = [];
  @Input() totalRecords: number = 0;
  @Input() rows: number = 5;
  @Input() first: number = 0;

  @Output() lazyLoad: EventEmitter<TableLazyLoadEvent> = new EventEmitter();

  constructor() { }

  ngOnInit() {
    this.lazyLoad.emit();
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    this.lazyLoad.emit(event);
  }
}