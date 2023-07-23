const path = require('path');

module.exports = {
    entry: './src/index.tsx',
    output: {
        path: path.resolve(__dirname, '../src/main/resources/static/stc'),
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
    ],
    devServer: {
        contentBase: './dist',
    },
};
