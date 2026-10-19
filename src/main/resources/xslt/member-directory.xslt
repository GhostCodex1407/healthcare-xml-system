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
            <title>Botswana Healthcare - Member Policy Directory</title>
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
                    background: linear-gradient(135deg, #1e3a8a 0%, #0284c7 100%);
                    color: #ffffff;
                    padding: 28px 36px;
                }
                .header h1 { font-size: 24px; font-weight: 700; margin-bottom: 6px; }
                .header p { font-size: 14px; opacity: 0.9; }
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
                .kpi-value { font-size: 24px; font-weight: 700; color: #0284c7; margin-top: 4px; }
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
                    vertical-align: middle;
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
                .badge-active { background-color: #dcfce7; color: #15803d; }
                .badge-suspended { background-color: #fef3c7; color: #b45309; }
                .badge-terminated { background-color: #fee2e2; color: #b91c1c; }
                .badge-plan { background-color: #e0f2fe; color: #0369a1; font-weight: 600; }
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
                    <h1>Botswana Medical Aid - Principal Member Directory</h1>
                    <p>Generated on <xsl:value-of select="@generatedDate"/> | Schema Architecture: Version <xsl:value-of select="@version"/></p>
                </div>

                <!-- KPI Metric Cards -->
                <div class="kpi-row">
                    <div class="kpi-card">
                        <div class="kpi-title">Total Members</div>
                        <div class="kpi-value"><xsl:value-of select="count(hc:members/hc:member)"/></div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-title">Active Policies</div>
                        <div class="kpi-value" style="color: #16a34a;">
                            <xsl:value-of select="count(hc:members/hc:member[@status='ACTIVE'])"/>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-title">Total Dependants</div>
                        <div class="kpi-value" style="color: #6366f1;">
                            <xsl:value-of select="count(hc:members/hc:member/hc:dependants/hc:dependant)"/>
                        </div>
                    </div>
                    <div class="kpi-card">
                        <div class="kpi-title">Accredited Plans</div>
                        <div class="kpi-value" style="color: #f59e0b;">
                            <xsl:value-of select="count(hc:benefitPlans/hc:plan)"/>
                        </div>
                    </div>
                </div>

                <!-- Table Content -->
                <div class="content">
                    <table>
                        <thead>
                            <tr>
                                <th>Policy #</th>
                                <th>Full Name</th>
                                <th>Omang (National ID)</th>
                                <th>Gender / DOB</th>
                                <th>Enrolled Benefit Plan</th>
                                <th>Contact Information</th>
                                <th style="text-align: center;">Dependants</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:for-each select="hc:members/hc:member">
                                <xsl:variable name="planRef" select="hc:enrolledPlanRef"/>
                                <tr>
                                    <td style="font-weight: 700; color: #0284c7;">
                                        <xsl:value-of select="@membershipNumber"/>
                                    </td>
                                    <td>
                                        <strong><xsl:value-of select="hc:firstName"/>&#160;<xsl:value-of select="hc:lastName"/></strong>
                                    </td>
                                    <td>
                                        <code><xsl:value-of select="hc:omangNumber"/></code>
                                    </td>
                                    <td>
                                        <xsl:value-of select="hc:gender"/>, <xsl:value-of select="hc:dateOfBirth"/>
                                    </td>
                                    <td>
                                        <span class="badge badge-plan">
                                            <xsl:value-of select="/hc:healthcareDatabase/hc:benefitPlans/hc:plan[@planCode=$planRef]/hc:planName"/>
                                        </span>
                                    </td>
                                    <td>
                                        <div><xsl:value-of select="hc:contact/hc:phone"/></div>
                                        <div style="font-size: 11px; color: #64748b;"><xsl:value-of select="hc:contact/hc:email"/></div>
                                    </td>
                                    <td style="text-align: center;">
                                        <xsl:choose>
                                            <xsl:when test="count(hc:dependants/hc:dependant) > 0">
                                                <strong><xsl:value-of select="count(hc:dependants/hc:dependant)"/></strong>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <span style="color: #94a3b8;">0</span>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>
                                    <td>
                                        <xsl:choose>
                                            <xsl:when test="@status='ACTIVE'">
                                                <span class="badge badge-active">Active</span>
                                            </xsl:when>
                                            <xsl:when test="@status='SUSPENDED'">
                                                <span class="badge badge-suspended">Suspended</span>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <span class="badge badge-terminated"><xsl:value-of select="@status"/></span>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </tbody>
                    </table>
                </div>

                <div class="footer">
                    <span>Generated via XSLT 1.0 Transformation Engine</span>
                    <span>Confidential Internal Healthcare Record System</span>
                </div>
            </div>
        </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
