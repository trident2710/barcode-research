import { Component } from '@angular/core';
import {ReedSolomonGeneratorPolyComponent} from "../reed-solomon-generator-poly/reed-solomon-generator-poly.component";
import {
    ReedSolomonErasureLocatorsPolyComponent
} from "../reed-solomon-erasure-locators-poly/reed-solomon-erasure-locators-poly.component";
import {
    ReedSolomonErrorLocatorsPolyComponent
} from "../reed-solomon-error-locators-poly/reed-solomon-error-locators-poly.component";

@Component({
  selector: 'reed-solomon-operations',
  standalone: true,
    imports: [
        ReedSolomonGeneratorPolyComponent,
        ReedSolomonErasureLocatorsPolyComponent,
        ReedSolomonErrorLocatorsPolyComponent
    ],
  templateUrl: './reed-solomon-operations.component.html',
  styleUrl: './reed-solomon-operations.component.css'
})
export class ReedSolomonOperationsComponent {

}
