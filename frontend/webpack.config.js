const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const Dotenv = require('dotenv-webpack');

module.exports = (env) => {
    // Use env variable to determine which .env file to use
    const currentEnv = env.NODE_ENV || 'development';
    const envPath = path.join(__dirname, `.env.${currentEnv}`);

    return {
        devtool: 'inline-source-map',
        entry: './src/index.tsx',
        output: {
            path: path.resolve(__dirname, './dist'),
            filename: 'bundle.js',
        },
        module: {
            rules: [
                {
                    test: /\.(ts|tsx)$/,
                    exclude: /node_modules/,
                    use: 'babel-loader',
                },
                {
                    test: /\.css$/,
                    use: ['style-loader', 'css-loader']
                },
                {
                    test: /\.scss$/,
                    use: ['style-loader', 'css-loader', 'sass-loader']
                },
                {
                    test: /\.less$/,
                    use: ['style-loader', 'css-loader', 'less-loader'],
                }
            ],
        },
        resolve: {
            extensions: ['.ts', '.tsx', '.js', '.jsx'],
        },
        plugins: [
            new HtmlWebpackPlugin({
                template: './src/index.html', // Source file
                filename: 'index.html'        // Destination file name
            }),
            new Dotenv({
                path: envPath
            })
        ],
        devServer: {
            hot: true,
            watchFiles: [path.resolve(__dirname)],
            liveReload: true,
            historyApiFallback: true,
            static: './dist',
            port: 3000,
        },
    };
};
