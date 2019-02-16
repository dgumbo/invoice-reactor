import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { OwlNativeDateTimeModule, OwlDateTimeModule } from 'ng-pick-datetime';

const sharedImports = [
  MatTabsModule,
  OwlDateTimeModule,
  OwlNativeDateTimeModule,
];
const sharedComponents = [
];
const sharedPipes = [
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ...sharedImports,
    ...sharedComponents,
    ...sharedPipes,
  ],
  exports: [
    ...sharedImports,
    ...sharedComponents,
    ...sharedPipes,
  ]
})
export class SharedModule { }
