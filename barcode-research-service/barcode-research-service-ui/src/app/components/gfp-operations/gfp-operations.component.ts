import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {GfpInverseComponent} from "../gfp-inverse/gfp-inverse.component";
import {GfpAddComponent} from "../gfp-add/gfp-add.component";

@Component({
  selector: 'gfp-operations',
  standalone: true,
  imports: [RouterOutlet, GfpInverseComponent, GfpAddComponent],
  templateUrl: './gfp-operations.component.html',
  styleUrl: './gfp-operations.component.css'
})
export class GfpOperationsComponent {
}
