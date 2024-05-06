import { Component } from '@angular/core';
import {ReedSolomonGeneratorPolyComponent} from "../reed-solomon-generator-poly/reed-solomon-generator-poly.component";

@Component({
  selector: 'reed-solomon-operations',
  standalone: true,
  imports: [
    ReedSolomonGeneratorPolyComponent
  ],
  templateUrl: './reed-solomon-operations.component.html',
  styleUrl: './reed-solomon-operations.component.css'
})
export class ReedSolomonOperationsComponent {

}
