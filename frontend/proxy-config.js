const proxy_config = [
    {
        context: ['/**'],
        target: 'https://tmdb.up.railway.app',
        secure: false
    }
]

module.exports = proxy_config