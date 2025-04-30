export default {
    base: "/service_reservation_doc/",
    title: 'Load Testing Course',
    description: 'Documentation for testing course',§
    themeConfig: {
        nav: [{ text: 'Home', link: '/' }],
        sidebar: {
            '/': [
                {
                    text: 'Introduction',
                    collapsible: true,
                    items: [
                        { text: '- Purpose object and subject of load testing', link: '/theory/PurposeObjectAndSubjectOfLoadTesting.md' },
                        { text: '- Load testing theory', link: '/theory/LoadTestingTheory.md' },
                        { text: '- Team composition and responsibilities in load testing', link: '/theory/TeamCompositionAndResponsibilitiesInLoadTesting.md' },
                    ]
                },
                {
                    text: 'Documentations',
                    collapsible: true,
                    items: [
                        { text: '- Methodology', link: '/documentations/Methodology.md' },
                        { text: '- Scenarios', link: '/documentations/Scenarios.md' },
                        { text: '- Reporting', link: '/documentations/Reporting.md' },
                    ]
                },
                {
                    text: 'Tools',
                    collapsible: true,
                    items: [
                        { text: '- Additional JMeter configuration', link: '/tools/JMeterConfiguration.md' },
                        { text: '- Calculate throughput with excel and timer', link: '/tools/ThroughputCalculator.md' },
                    ]
                },
                {
                    text: 'Analytics',
                    collapsible: true,
                    items: [
                        { text: '- Apache JMeter Dashboard Metrics (InfluxDB Backend Listener)', link: '/analysis/ApacheJMeterDashboard.md' },
                        { text: '- Cadvisor Exporter Dashboard - Metric Breakdown and Analysis', link: '/analysis/CAdvisorDashboardMetrics.md' },
                        { text: '- Node Exporter Full', link: '/analysis/NodeExporterDashboards.md' },
                        { text: '- Postgres Exporter Dashboard - Metric Breakdown and Analysis', link: '/analysis/PostgresExporter.md' },
                    ]
                },
                {
                    text: 'Process',
                    items: [
                        { text: '- Microservices architecture', link: '/process/MicroservicesArchitecture.md' },
                        { text: '- PostProcessing', link: '/process/PostProcessing.md' },
                        { text: '- Building an Iterative Load Testing process', link: '/process/BuildingAnIterativeLoadTestingProcess.md' },
                        { text: '- Lifecycle', link: '/process/Lifecycle.md' },
                    ]
                },

            ]
        }
    }
}
