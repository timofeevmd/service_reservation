import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
    plugins: [react()],
    resolve: {
        extensions: [".js", ".jsx"],
    },
    root: '.',
    publicDir: 'public',
    server: {
        open: true,
        port: 3000,
        strictPort: true,
    },
    build: {
        outDir: 'dist',
        emptyOutDir: true,
        copyPublicDir: true,
        rollupOptions: {
            input: 'index.html',
        },
    },
    preview: {
        port: 4173,
        strictPort: true,
    },
    define: {
        'process.env': process.env
    },
    base: './',
});
