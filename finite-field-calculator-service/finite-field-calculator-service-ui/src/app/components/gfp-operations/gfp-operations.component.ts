import {Component} from '@angular/core';
import {GfpInverseComponent} from "../gfp-inverse/gfp-inverse.component";
import {GfpAddComponent} from "../gfp-add/gfp-add.component";
import {GfpExpComponent} from "../gfp-exp/gfp-exp.component";

@Component({
  selector: 'gfp-operations',
  standalone: true,
  imports: [GfpInverseComponent, GfpAddComponent, GfpExpComponent],
  templateUrl: './gfp-operations.component.html',
  styleUrl: './gfp-operations.component.css'
})
export class GfpOperationsComponent {
}
