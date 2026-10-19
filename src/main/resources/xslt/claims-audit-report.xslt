<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:hc="http://www.healthsystem.bw/schema/healthcare"
                exclude-result-prefixes="hc">

    <xsl:output method="html" encoding="UTF-8" indent="yes" doctype-system="about:blank"/>

    <xsl:template match="/hc:healthcareDatabase">
        <html lang="en">
        <head>
            <meta charset="UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <title>Botswana Healthcare - Claims &amp; Financial Audit Statement</title>
            <style>
                * { box-sizing: border-box; margin: 0; padding: 0; }
                body {
                    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
                    background-color: #f1f5f9;
                    color: #0f172a;
                    padding: 30px;
                }
                .container {
                    max-width: 1200px;
                    margin: 0 auto;
                    background: #ffffff;
                    border-radius: 12px;
                    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -2px rgba(0, 0, 0, 0.1);
                    overflow: hidden;
                }
                .header {
                    background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
                    color: #ffffff;
                    padding: 28px 36px;
                }
                .header h1 { font-size: 24px; font-weight: 700; margin-bottom: 6px; }
                .header p { font-size: 14px; color: #94a3b8; }
                .kpi-row {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
                    gap: 16px;
                    padding: 24px 36px;
                    background-color: #f8fafc;
                    border-bottom: 1px solid #e2e8f0;
                }
                .kpi-card {
                    background: #ffffff;
                    padding: 16px;
                    border-radius: 8px;
                    border: 1px solid #e2e8f0;
                }
                .kpi-title { font-size: 12px; text-transform: uppercase; color: #64748b; font-weight: 600; }
                .kpi-value { font-size: 22px; font-weight: 700; margin-top: 4px; }
                .content { padding: 28px 36px; }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    font-size: 13px;
                }
                th {
                    background-color: #f8fafc;
                    color: #475569;
                    font-weight: 600;
                    text-align: left;
                    padding: 12px 14px;
                    border-bottom: 2px solid #e2e8f0;
                }
                td {
                    padding: 12px 14px;
                    border-bottom: 1px solid #f1f5f9;
                    vertical-align: top;
                }
                tr:hover td { background-color: #f8fafc; }
                .badge {
                    display: inline-block;
                    padding: 3px 8px;
                    border-radius: 6px;
                    font-size: 11px;
                    font-weight: 600;
                    text-transform: uppercase;
                }
                .badge-approved { background-color: #dcfce7; color: #15803d; }
                .badge-partial { background-color: #e0f2fe; color: #0369a1; }
                .badge-submitted { background-color: #fef3c7; color: #b45309; }
                .badge-rejected { background-color: #fee2e2; color: #b91c1c; }
                .line-item-box {
                    margin-top: 6px;
                    padding: 6px 10px;
                    background-color: #f8fafc;
                    border-left: 3px solid #cbd5e1;
                    font-size: 12px;
                    color: #475569;
                }
                .footer {
                    padding: 16px 36px;
                    background-color: #f8fafc;
                    border-top: 1px solid #e2e8f0;
                    font-size: 12px;
                    color: #64748b;
                    display: flex;
                    justify-content: space-between;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Healthcare Medical Claims &amp; Financial Utilization Audit</h1>
                    <p>Financial Statement in Botswana Pula (BWP) | Report Date: <xsl:value-of select="@generatedDate"/></p>
                </div>

                <!-- KPI Metric Cards -->
                <div class="kpi-row">
                    <div class="kpi-card">
                        <div class="kpi-title">Total Claims Audited</div>
                        <div class="kpi-value" style="color: #0f172a;"><xsl:value-of select="count(hc:claims/hc:claim)"/></div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-title">Total Approved Claims</div>
                        <div class="kpi-value" style="color: #16a34a;">
                            <xsl:value-of select="count(hc:claims/hc:claim[@status='APPROVED' or @status='PARTIALLY_APPROVED'])"/>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-title">Rejected Claims</div>
                        <div class="kpi-value" style="color: #dc2626;">
                            <xsl:value-of select="count(hc:claims/hc:claim[@status='REJECTED'])"/>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-title">Healthcare Providers</div>
                        <div class="kpi-value" style="color: #0284c7;">
                            <xsl:value-of select="count(hc:providers/hc:provider)"/>
                        </div>
                    </div>
                </div>

                <!-- Table Content -->
                <div class="content">
                    <table>
                        <thead>
                            <tr>
                                <th>Claim Ref</th>
                                <th>Member Policy</th>
                                <th>Healthcare Provider</th>
                                <th>Service Date</th>
                                <th style="text-align: right;">Total Claimed</th>
                                <th style="text-align: right;">Total Approved</th>
                                <th style="text-align: right;">Member Copay</th>
                                <th style="text-align: center;">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:for-each select="hc:claims/hc:claim">
                                <xsl:variable name="provRef" select="hc:providerRef"/>
                                <xsl:variable name="memRef" select="hc:memberRef"/>
                                <tr>
                                    <td style="font-weight: 700; color: #0f172a;">
                                        <xsl:value-of select="@claimRef"/>
                                    </td>
                                    <td>
                                        <div><strong><xsl:value-of select="$memRef"/></strong></div>
                                        <div style="font-size: 11px; color: #64748b;">
                                            <xsl:value-of select="/hc:healthcareDatabase/hc:members/hc:member[@membershipNumber=$memRef]/hc:firstName"/>&#160;<xsl:value-of select="/hc:healthcareDatabase/hc:members/hc:member[@membershipNumber=$memRef]/hc:lastName"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div><xsl:value-of select="/hc:healthcareDatabase/hc:providers/hc:provider[@practiceNumber=$provRef]/hc:facilityName"/></div>
                                        <div style="font-size: 11px; color: #64748b;"><xsl:value-of select="$provRef"/></div>
                                    </td>
                                    <td>
                                        <xsl:value-of select="hc:serviceDate"/>
                                    </td>
                                    <td style="text-align: right; font-weight: 600;">
                                        BWP <xsl:value-of select="format-number(hc:totalClaimed, '#,##0.00')"/>
                                    </td>
                                    <td style="text-align: right; font-weight: 700; color: #16a34a;">
                                        BWP <xsl:value-of select="format-number(hc:totalApproved, '#,##0.00')"/>
                                    </td>
                                    <td style="text-align: right; color: #b45309;">
                                        BWP <xsl:value-of select="format-number(hc:memberCopay, '#,##0.00')"/>
                                    </td>
                                    <td style="text-align: center;">
                                        <xsl:choose>
                                            <xsl:when test="@status='APPROVED'">
                                                <span class="badge badge-approved">Approved</span>
                                            </xsl:when>
                                            <xsl:when test="@status='PARTIALLY_APPROVED'">
                                                <span class="badge badge-partial">Partially Approved</span>
                                            </xsl:when>
                                            <xsl:when test="@status='REJECTED'">
                                                <span class="badge badge-rejected">Rejected</span>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <span class="badge badge-submitted"><xsl:value-of select="@status"/></span>
                                            </xsl:otherwise>
                                        </xsl:choose>

                                        <!-- Show rejection reason if available -->
                                        <xsl:if test="hc:rejectionReason">
                                            <div style="font-size: 10px; color: #b91c1c; margin-top: 4px; max-width: 180px;">
                                                <xsl:value-of select="hc:rejectionReason"/>
                                            </div>
                                        </xsl:if>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </tbody>
                    </table>
                </div>

                <div class="footer">
                    <span>Generated via XSLT 1.0 Transformation Engine</span>
                    <span>Botswana Medical Aid Auditing &amp; Governance Unit</span>
                </div>
            </div>
        </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
