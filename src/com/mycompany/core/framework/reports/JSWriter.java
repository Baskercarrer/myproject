package com.mycompany.core.framework.reports;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JSWriter
{
  private PrintWriter writer;

  public void generateTestCaseReportJS(String paramString, int[] paramArrayOfInt)
    throws FileNotFoundException
  {
    this.writer = new PrintWriter(paramString);
    this.writer.println("$(document).ready(function(){var plot1 = $.jqplot('holder', [[['" + paramArrayOfInt[0] + " : Passed Test Cases'," + paramArrayOfInt[0] + "],['" + paramArrayOfInt[1] + " : Failed Test Cases'," + paramArrayOfInt[1] + "],['" + paramArrayOfInt[2] + " : Skipped Test Cases'," + paramArrayOfInt[2] + "]]], {" + "gridPadding: {" + "top:0, " + "            bottom:0, " + "            left:0," + "            right:0" + "        }," + "        seriesDefaults:{" + "            renderer:$.jqplot.PieRenderer," + "            trendline:{" + "                show:false" + "            }," + "            rendererOptions: {" + "                padding: 29," + "                showDataLabels: true," + "                dataLabelThreshold:1," + "                dataLabelNudge: 62," + "                seriesColors: ['#A2BF2F', '#BF2F2F','#2F69BF']" + "                ," + "                startAngle:165," + "                shadowOffset:1," + "                shadowAlpha:0.02," + "                sliceMargin:1.2" + "            }" + "        }," + " grid: { background: '#ffffff'}," + "        legend:{" + "            show:true," + "            location:'e'" + "        }      " + "    });" + "});");
    this.writer.flush();
    this.writer.close();
  }

  public void generateCompleteReportJS(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
    throws FileNotFoundException
  {
    this.writer = new PrintWriter(paramString1);
    this.writer.println("$(document).ready(function(){");
    this.writer.println("var s1 = [" + paramString2 + "];");
    this.writer.println("var s2 = [" + paramString3 + "];");
    this.writer.println("var s3 = [" + paramString4 + "];");
    this.writer.print("var ticks = [");
    for (int i = paramInt1; i <= paramInt2; i++)
    {
      this.writer.print(i);
      if (i != paramInt2)
        this.writer.print(",");
    }
    this.writer.print("];");
    this.writer.println("plot2 = $.jqplot('chart', [s1, s2,s3], {animate:true,");
    this.writer.println("seriesDefaults: { renderer:$.jqplot.BarRenderer, pointLabels: { show: true  } },");
    this.writer.println("series: [ { label: 'Passed Test Cases'  }, { label: 'Failed Test Cases'  }, { label: 'Skipped Test Cases'   }  ],");
    this.writer.println("axes: { xaxis: {label: 'Executions', renderer: $.jqplot.CategoryAxisRenderer,");
    this.writer.println("rendererOptions:{ varyBarColor : true },  ticks: ticks   },yaxis:{label: 'Test Cases'} },");
    this.writer.println("grid: { background: '#ffffff',    drawGridLines: true,   gridLineColor: '#cccccc',   borderColor: '#cccccc', ");
    this.writer.println(" borderWidth: 0.5,  shadow: true,  shadowOffset: 1,  shadowWidth: 0.2,  shadowDepth: 0.2 },");
    this.writer.println("legend: { show: true, placement: 'outsideGrid',  location:'e' },");
    this.writer.println("seriesColors: [ \"#A2BF2F\", \"#BF2F2F\", \"#2F69BF\"],");
    this.writer.println("highlighter: {show: true}");
    this.writer.println("});");
    this.writer.println("$('chart').height($('#ParentElement').height() * 0.96);");
    this.writer.println("$('chart').width($('#ParentElement').width() * 0.96);");
    this.writer.println("});");
    this.writer.flush();
    this.writer.close();
  }
}