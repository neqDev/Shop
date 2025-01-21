import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Chart, ChartData, registerables } from 'chart.js';
import { AdminOrderService } from '../admin-order.service';




@Component({
  selector: 'app-admin-order-stats',
  templateUrl: './admin-order-stats.component.html',
  styleUrls: ['./admin-order-stats.component.scss']
})
export class AdminOrderStatsComponent implements AfterViewInit{
  
  private data = {
    labels: [],
    datasets: [
      {
        label: 'Zamówienia',
        data: [],
        borderColor: '#FF3F7C',
        backgroundColor: '#FF7A9F',
        order: 1,
        yAxisID: 'y'
      },
      {
        label: 'Sprzedaż',
        data: [],
        borderColor: '#0088FF',
        backgroundColor: '#00A1FF ',
        type: 'line',
        order: 0,
        yAxisID: 'y1'
      }
    ]
  } as ChartData;

@ViewChild("stats") private stats!: ElementRef // referencja do elementu canvas stats
chart!: Chart;
ordersCount: number = 0;
salesSum: number = 0;

  constructor(private adminOrderService: AdminOrderService){
    Chart.register(...registerables) // rejestracja komponentow wykresow

  }

  ngAfterViewInit(): void {
    this.setupChart();
    this.getSalesStatistics();
  }
  getSalesStatistics() {
   
    this.adminOrderService.getSalesStatistics()
    .subscribe(stats =>{
      console.error( stats.orders)
  
      this.data.labels = stats.labels;
      this.data.datasets[0].data = stats.orders; // zamowienia
      this.data.datasets[1].data = stats.sales; // sprzedaż
      this.chart.update(); // aktualizuje wykres danymi ktore zostaly pobrane z uslugi
   
      this.ordersCount = stats.ordersCount;
      this.salesSum = stats.salesSum;
     
    });
  }

  setupChart() {
    this.chart = new Chart(this.stats.nativeElement, 
      {
        type: 'bar',
        data: this.data,
        options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: 'Wykres sprzedaży'
          }
        },
        scales: {
          y: {
            type: 'linear',
            display: true,
            position: 'left',
          },
          y1: {
            type: 'linear',
            display: true,
            position: 'right',
                // grid line settings
            grid: {
              drawOnChartArea: false, // only want the grid lines for one axis to show up
            },
          }
        }
      }
      });
    }
}
